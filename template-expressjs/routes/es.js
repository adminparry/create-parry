var express = require('express');
var router = express.Router();

const es  = require('../elasticsearch');

router.get('/suggest/:input', (req, res, next)  => {
    const { params } = req;

    es.getSuggestions(params.input).then(result  => {
        res.json(result)
    }) 
})

module.exports = router;