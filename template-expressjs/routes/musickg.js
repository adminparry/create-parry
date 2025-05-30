const express = require("express");

const router = express.Router();
const https = require("https");
const urls = require("../config/kgurl");

router.get("/music", (req, res, next) => {
  const callback = "callback123";
  https
    .get(urls.musicUrl(callback), (res) => {})
    .on("error", () => {
      res.json({ data: "timeout" });
    });
});
  