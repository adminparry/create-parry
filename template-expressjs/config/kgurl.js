// 音乐检索列表
module.exports.searchUrl = (keyword, callback) =>
  `https://searchtip.kugou.com/getSearchTip?MusicTipCount=5&MVTipCount=2&albumcount=2&keyword=${keyword}&callback=${callback}&_=1678175597178`;

module.exports.listUrl = (keyword, callback) => `
https://complexsearch.kugou.com/v2/search/song?callback=${callback}&srcappid=2919&clientver=1000&clienttime=1678175797011&mid=7232c1ad6c7e174757b410d4e2bb3377&uuid=7232c1ad6c7e174757b410d4e2bb3377&dfid=3exmma24ANvO2Hbczn49Fvco&keyword=${keyword}&page=1&pagesize=30&bitrate=0&isfuzzy=0&inputtype=0&platform=WebFilter&userid=0&iscorrection=1&privilege_filter=0&filter=10&token=&appid=1014&signature=270b7512e11225bbcc727850bb8069fb
`;
module.exports.musicUrl =
  (callback) => `https://wwwapi.kugou.com/yy/index.php?r=play/getdata&callback=${callback}&dfid=3exmma24ANvO2Hbczn49Fvco&appid=1014&mid=7232c1ad6c7e174757b410d4e2bb3377&platid=4&encode_album_audio_id=84yrwtf1&_=1678175934831
`;
