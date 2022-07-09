const KafkaBase = require("../KafkaBase/KafkaBase");
const { CompressionTypes } = require('kafkajs');

const KafkaProducer = {
    producer: KafkaBase.producer(),
    sendMessage: (msg, topic) => {
        return KafkaProducer.producer
          .send({
            topic: topic,
            compression: CompressionTypes.GZIP,
            messages: [{
                key: null,
                value: JSON.stringify(msg)
            }],
          })
          .then(console.log)
    }
}

module.exports = KafkaProducer;