const { MongoClient } = require('mongodb');
const PDFDocument = require('pdfkit');
const fs = require('fs');
const path = require('path');

async function connectToMongo() {
    const url = 'mongodb://root:password@localhost:27017';
    const dbName = 'stackexchange'; 

    const client = new MongoClient(url, { useNewUrlParser: true, useUnifiedTopology: true });

    await client.connect();
    console.log('Conectat a MongoDB');
    const db = client.db(dbName);
    return db.collection('posts');  // Asegúrate de que la colección sea 'posts'
}

async function checkDocuments() {
    const collection = await connectToMongo();

    const count = await collection.countDocuments();
    console.log(`Número total de documentos en 'posts': ${count}`);

    const sample = await collection.findOne(); // Obtener un ejemplo de documento
    console.log("Ejemplo de documento:", sample);
}

checkDocuments();

async function getQuestionsAboveAverage() {
    const collection = await connectToMongo();

    const avgViewCount = await collection.aggregate([
        { 
            $group: { 
                _id: null, 
                averageViewCount: { $avg: { $toDouble: "$question.ViewCount" } }  // Convertir ViewCount a número
            } 
        }
    ]).toArray();

    if (avgViewCount.length === 0) {
        console.log("No se pudo calcular la media de ViewCount.");
        return [];
    }

    const averageViewCount = avgViewCount[0].averageViewCount;
    console.log(`ViewCount promedio: ${averageViewCount}`);

    const questions = await collection.find({
        'question.ViewCount': { $gt: averageViewCount.toString() }  // Comparar con ViewCount convertido a número
    }).limit(100).toArray();
    
    console.log(`Preguntas encontradas: ${questions.length}`);
    return questions;
}

async function getQuestionsByTitles() {
    const collection = await connectToMongo();
    const letters = ["pug", "wig", "yak", "nap", "jig", "mug", "zap", "gag", "oaf", "elf"];
    const regex = new RegExp(letters.join('|'), 'i'); 

    const questions = await collection.find({ 'question.Title': { $regex: regex } }).limit(100).toArray();
    
    console.log(`Preguntas con títulos coincidentes: ${questions.length}`);
    return questions;
}

async function generatePDF(questions, filename) {
    const dir = path.dirname(filename);

    // Verifica si la carpeta existe y créala si no
    if (!fs.existsSync(dir)) {
        fs.mkdirSync(dir, { recursive: true });
    }

    return new Promise((resolve, reject) => {
        const doc = new PDFDocument();
        const stream = fs.createWriteStream(filename);

        stream.on('finish', resolve); // Esperar hasta que termine de escribir
        stream.on('error', reject);   // Capturar errores

        doc.pipe(stream);

        doc.fontSize(16).text('Títols de les preguntes:', { underline: true });

        questions.forEach((question, index) => {
            doc.fontSize(12).text(`${index + 1}. ${question.question?.Title || "Sin título"}`);
        });

        doc.end();
    });
}

async function main() {
    try {
        const questionsAboveAverage = await getQuestionsAboveAverage();
        console.log(`Número de preguntes amb ViewCount més gran que la mitjana: ${questionsAboveAverage.length}`);
        console.log(questionsAboveAverage);

        if (questionsAboveAverage.length > 0) {
            await generatePDF(questionsAboveAverage, './data/out/informe1.pdf');
        }

        const questionsByTitle = await getQuestionsByTitles();
        console.log(`Número de preguntes amb títols que contenen lletres específiques: ${questionsByTitle.length}`);
        
        if (questionsByTitle.length > 0) {
            await generatePDF(questionsByTitle, './data/out/informe2.pdf');
        }
        
        console.log("Els informes PDF s'han generat correctament.");
        process.exit(0); 
    } catch (error) {
        console.error('Error en la consulta o la generació de PDF:', error);
        process.exit(1); 
    }
}

main();
