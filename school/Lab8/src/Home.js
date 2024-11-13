
const Home = (language) => {
  var lang = require('./Text.json');
  var text;
  if (language.language === "english") {
    text = lang[0];
  } else {
    text = lang[1];
  }

  return (
    <div className="home">
      <div className="content fullContent">
        <h1>{text.homeTitle}</h1>
        <p>{text.homeLine1}</p>
        <p>{text.homeLine2}</p>
        <p>{text.homeLine3}</p>
        <p>{text.homeLine4}</p>

      </div>
    </div>
  );
}

export default Home;
