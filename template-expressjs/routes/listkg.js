const express = require('express');

const router  =  express.Router();

router.get('/list', (req, res, next)  => {
    const { query , params }  = req;
    const callback = 'callback123';
        
    res.json({})
})