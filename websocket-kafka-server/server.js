const express = require('express');
const KafkaConsumer = require("./KafkaConsumer/KafkaConsumer");
const KafkaProducer = require("./KafkaProducer/KafkaProducer");
const Auth = require("./Auth/auth");
const { consumer } = require('./KafkaConsumer/KafkaConsumer');
const app = express()
const port = 5000
const server = app.listen(port, () => {
    console.log(`Example app listening at http://localhost:${port}`)
})

const io = require("socket.io")(server, {
    cors: {
        origin: "*"
    }
});

app.use(express.json());
app.use(Auth);

const topic = "TEST-TOPIC";

const runCons = async () => {
    await KafkaConsumer.consumer.connect()
    await KafkaConsumer.consumer.subscribe({ topic, fromBeginning: true })
    await KafkaConsumer.consumer.run({
        eachMessage: async ({ topic, partition, message }) => { 
            let value = message.value.toString();
            let key = message.key.toString();
            console.log(key + " | " + value);
            io.emit(key, value);
            consumer.commitOffsets();   
        },
    })
}


io.on('connection', function(socket){
    
});

runCons().catch(e => console.error(`[example/consumer] ${e.message}`, e));