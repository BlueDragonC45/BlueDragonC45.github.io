
const Contact = (language) => {
  var lang = require('./Text.json');
  var text;
  if (language.language === "english") {
    text = lang[0];
  } else {
    text = lang[1];
  }

  return (
    <div className="contact">
      <div className="content fullContent">
        <h1>{text.contactTitle}</h1>
        <p>{text.contactLine1}</p>
        <p>{text.contactLine2}</p>
        <p>{text.contactLine3}</p>
        <address style={{fontSize: '1.5rem', marginLeft: '3vw'}}>
          <strong>DRACONIC Games</strong><br />
          1900 Drain Ln.<br />
          Norwood, Ontario<br />
          Phone: 705-933-9106
      </address>
      <p>{text.contactLine4}</p>
      </div>
    </div>
  );
}

export default Contact;
