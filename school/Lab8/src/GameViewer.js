import star from './Assets/star.png';
import profile from './Assets/profile.png';
import { useHistory } from 'react-router-dom';

const GameViewer = (language) => {
  var value = (window.location.pathname).split('/'); //Finds the index of the game the opened this gameviewer instance, at value[2]
  var data;

  var lang = require('./Text.json');
  var text;
  if (language.language === "english") {
    text = lang[0];
    data = require('./ENGames.json');
  } else {
    text = lang[1];
    data = require('./FRGames.json');
  }

  var obj = data[value[3]];
  const tagsArr = (obj.tags).split(" ");
  tagsArr.pop();

  const history = useHistory();
  const buttonClick = () => {
    const path = "/" + text.current + "/download/";
    history.push(path);
  }

  return (
    <div className="gameViewer">
    <h1 className="title"> {obj.title} </h1>
      <div className="row no-gutters">
        <div className="content longContent col-">
          <p id="gameDesc"> {obj.desc} </p>
        </div>
        <div className="content smallContent col-">
          <p id="rating">{text.gameviewerLine1}{obj.rating} <img src={star} alt="rating star" width="18px" height="18px" style={{marginTop: '-10px'}}/></p>
          <p style={{marginBottom: '0.5vh' }}>{text.gameviewerLine2}</p>
          <div style={{padding: '0', margin: '-1vh 1vw 0 3vw'}}>
            {tagsArr.map(function(tags, index) {
              console.log(tags);
              switch (tags) {
                case "action":
                  return <span id="tag" key={index}> {text.action} </span>
                case "adventure":
                  return <span id="tag" key={index}> {text.adventure} </span>
                case "rpg":
                  return <span id="tag" key={index}> {text.rpg} </span>
                case "puzzle":
                  return <span id="tag" key={index}> {text.puzzle} </span>
                case "singleplayer":
                  return <span id="tag" key={index}> {text.singleplayer} </span>
                case "coop":
                  return <span id="tag" key={index}> {text.coop} </span>
                case "mmo":
                  return <span id="tag" key={index}> {text.mmo} </span>
                default:
                  return <span id="tag" key={index}></span>
              }
            })}
          </div>
          <button id="gameViewerButtons" onClick={buttonClick}> <strong>{text.gameviewerLine3}</strong> {obj.price}  </button>
        </div>
      </div>
      <div className="row no-gutters">
        <div className="content longContent row col-">
          <div className="reviewProfile col-">
          <p> <img src={profile} alt="profile" width="40px" height="40px"/> <strong> {obj.reviewer} </strong></p>
          <p> {text.gameviewerLine4}{obj.reviewerrateing} / 5 <img src={star} alt="rating star" width="18px" height="18px" style={{marginTop: '-10px'}}/></p>
        </div>
        <div className="reviewContent col-">
          <p> {obj.review} </p>
        </div>

        </div>
        <div className="content smallContent col-">
          <p>{text.gameviewerLine5} </p>
          <p style={{ width: '100%', textAlign: 'center', margin: '0px'}}>
              <img src={star} alt="rating star" width="40vw" height="40vw"/>
              <img src={star} alt="rating star" width="40vw" height="40vw"/>
              <img src={star} alt="rating star" width="40vw" height="40vw"/>
              <img src={star} alt="rating star" width="40vw" height="40vw"/>
              <img src={star} alt="rating star" width="40vw" height="40vw"/></p>
          <button id="gameViewerButtons">{text.gameviewerLine6}</button>
        </div>
      </div>
    </div>
  );
}

export default GameViewer;
