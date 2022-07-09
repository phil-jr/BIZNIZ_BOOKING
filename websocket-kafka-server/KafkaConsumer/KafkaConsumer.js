const KafkaBase = require("../KafkaBase/KafkaBase");

const KafkaConsumer = {
    consumer:  KafkaBase.consumer({ groupId: 'test-group' })
}

module.exports = KafkaConsumer;