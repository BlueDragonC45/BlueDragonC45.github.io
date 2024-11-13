import { Link } from 'react-router-dom'

const Breadcrumbs = (path, language) => {
  var lang = require('./Text.json');
  var text;
  if (path.language === "english") {
    text = lang[0];
  } else {
    text = lang[1];
  }

  var pathHome = "/" + text.current + "/";
  var pathExplore = pathHome + "explore/";
  var pathContact = pathHome + "contact/";
  var pathDownload = pathHome + "download/";

  const decoder = (currentPath) => {
    var val;
    switch (currentPath) {
      case "Home":
      val = (
        <div className="breadcrumbs">
          <div className="breadcrumbsContent">
            <p> <strong>></strong><Link className="link" to={pathHome}>{text.navbar1}</Link> </p>
          </div>
        </div>
      );
      break;
      case "Explore":
      val = (
        <div className="breadcrumbs">
          <div className="breadcrumbsContent">
            <p> <strong>></strong><Link className="link" to={pathHome}>{text.navbar1}</Link>
                <strong>></strong><Link className="link" to={pathExplore}>{text.navbar2}</Link> </p>
          </div>
        </div>
      );
      break;
      case "Contact":
      val = (
        <div className="breadcrumbs">
          <div className="breadcrumbsContent">
            <p> <strong>></strong><Link className="link" to={pathHome}>{text.navbar1}</Link>
                <strong>></strong><Link className="link" to={pathContact}>{text.navbar3}</Link> </p>
          </div>
        </div>
      );
      break;
      case "Gameviewer":
      const value = (window.location.pathname).split('/'); //Finds the index of the game the opened this gameviewer instance, at value[2]
      const data = require('./ENGames.json');
      const obj = data[value[3]];
      const path = pathExplore + value[3];
      val = (
        <div className="breadcrumbs">
          <div className="breadcrumbsContent">
            <p> <strong>></strong><Link className="link" to={pathHome}>{text.navbar1}</Link>
                <strong>></strong><Link className="link" to={pathExplore}>{text.navbar2}</Link>
                <strong>></strong><Link className="link" to={path} >{obj.title}</Link> </p>
          </div>
        </div>
      );
      break;
      case "Download":
      val = (
        <div className="breadcrumbs">
          <div className="breadcrumbsContent">
            <p> <strong>></strong><Link className="link" to={pathHome}>{text.navbar1}</Link>
                <strong>></strong><Link className="link" to={pathExplore}>{text.navbar2}</Link>
                <strong>></strong><Link className="link" to={pathDownload} >{text.navbar5}</Link> </p>
          </div>
        </div>
      );
      break;
      default:
      val = (
        <div className="breadcrumbs">
          <div className="breadcrumbsContent">
            <p> <strong>></strong><Link className="link" to={pathHome}>{text.navbar1}</Link> </p>
          </div>
        </div>
      );


  }
  return val;

  }
  return (
    decoder(path.path)
  );
}

export default Breadcrumbs;
