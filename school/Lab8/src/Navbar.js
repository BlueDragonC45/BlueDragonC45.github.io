import logo from './Assets/logo.png';
import { Link } from 'react-router-dom'

const Navbar = (language) => {
  var lang = require('./Text.json');
  var text;
  if (language.language === "english") {
    text = lang[0];
  } else {
    text = lang[1];
  }
  const currentLang = text.current;
  const homePath = "/" + currentLang;
  const explorePath = "/" + currentLang + "/explore";
  const contactPath = "/" + currentLang + "/contact";
  const switchPath = "/" + text.switch;

  return (
    <div className="navbar">
      <img src={logo} alt="logo" height="100px" width="75px" />
      <h1> <strong> DRACONIC </strong></h1>
      <div className="links">
        <Link to={homePath}>{text.navbar1}</Link>
        <Link to={explorePath}> {text.navbar2}</Link>
        <Link to={contactPath}>{text.navbar3}</Link>
        <a href={switchPath}>{text.navbar4}</a>
      </div>
    </div>
  );
}

export default Navbar;
