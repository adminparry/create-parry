const https = require("https");

module.exports = (urlfn, args, callback) => {
  const cb = "jQuery1910615581513109646_1678175597171";
  return https.get(urlfn(...args, cb), (resp) => {
    let resData = "";
    resp.on("data", (data) => {
      resData += data;
    });
    resp.on("end", () => {
      const data = resData.substring(cb.length + 1, resData.length - 2);
      callback && callback(JSON.parse(data));
    });
  });
};
