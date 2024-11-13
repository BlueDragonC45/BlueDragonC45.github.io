
const Download = (language) => {
  var lang = require('./Text.json');
  var text;
  if (language.language === "english") {
    text = lang[0];
  } else {
    text = lang[1];
  }

  return (
    <div className="download">
      <div className="content fullContent">
        <h1>{text.downloadTitle}</h1>
        <p>{text.downloadLine1}</p>
        <p>{text.downloadLine2}</p>
        <p>{text.downloadLine3}</p>
        <p>{text.downloadLine4}</p>
        <p>{text.downloadLine5}</p>

      </div>
    </div>
  );
}

export default Download;
