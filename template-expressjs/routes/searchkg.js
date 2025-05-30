var express = require("express");
var router = express.Router();
const searchUrl = require("../config/kgurl");
const requestJsonp = require("../utils/request");

/* GET home page. */
router.get("/", function (req, res, next) {
  const { params, query } = req;
  console.log(params, query);
  if (query.keyword) {
    requestJsonp(searchUrl.searchUrl, [query.keyword], (resData) => {
      res.json({
        data: resData,
      });
    }).on("error", () => {
      res.json({ msg: "timeout" });
    });
  } else {
    res.json({});
  }
});

module.exports = router;
