const es = require("elasticsearch");

const esClient = new es.Client({
  host: "localhost:9200",
});

const indexName = "document";

function getSuggestions(input) {
  return esClient.search({
    index: indexName,
    body: {
      query: {
        match: {
          title: input,
        },
      },
    },
  });
}

module.exports.getSuggestions = getSuggestions;
