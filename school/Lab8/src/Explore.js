import terraria from './Assets/terraria.png';
import fortnite from './Assets/fortnite.png';
import valheim from './Assets/valheim.png';
import deadbydaylight from './Assets/deadbydaylight.png';
import witness from './Assets/witness.png';
import ark from './Assets/ark.png';
import subnautica from './Assets/subnautica.png';
import candycrush from './Assets/candycrush.png';
import rocketleague from './Assets/rocketleague.png';
import eve from './Assets/eve.png';
import Card from "react-bootstrap/Card"
import { useState } from 'react';
import { useHistory } from 'react-router-dom';

const Explore = (language) => {
  var lang = require('./Text.json');
  var text;
  if (language.language === "english") {
    text = lang[0];
  } else {
    text = lang[1];
  }

  var data = require('./ENGames.json');


  const allGames = [
    {image: terraria, title: data[0].title, id: "0"},
    {image: fortnite, title: data[1].title, id: "1"},
    {image: valheim, title: data[2].title, id: "2"},
    {image: deadbydaylight, title: data[3].title, id: "3"},
    {image: witness, title: data[4].title, id: "4"},
    {image: ark, title: data[5].title, id: "5"},
    {image: subnautica, title: data[6].title, id: "6"},
    {image: candycrush, title: data[7].title, id: "7"},
    {image: rocketleague, title: data[8].title, id: "8"},
    {image: eve, title: data[9].title, id: "9"},
  ];

  const [games, setGames] = useState(allGames);

  const history = useHistory();
  const cardClick = (val) => {
    const clickPath = "/" + text.current + "/gameviewer/" + val;
    history.push(clickPath);
  }

  const checkFilters = (index) => {
    const tags = data[index].tags;
    if ((document.getElementById("action").checked && !tags.includes("action")) ||
        (document.getElementById("adventure").checked && !tags.includes("adventure")) ||
        (document.getElementById("rpg").checked && !tags.includes("rpg")) ||
        (document.getElementById("puzzle").checked && !tags.includes("puzzle")) ||
        (document.getElementById("singleplayer").checked && !tags.includes("singleplayer")) ||
        (document.getElementById("coop").checked && !tags.includes("coop")) ||
        (document.getElementById("mmo").checked && !tags.includes("mmo"))) {
      return false;
    } else {
      if ((document.getElementById("free").checked && !tags.includes("free")) ||
          (document.getElementById("lessThanFive").checked && !tags.includes("lessthanfive")) ||
          (document.getElementById("lessThanTwenty").checked && !tags.includes("lessthantwenty")) ||
          (document.getElementById("lessThanFifty").checked && !tags.includes("lessthanfifty")) ||
          (document.getElementById("lessThanHundred").checked && !tags.includes("lessthanhundred")) ||
          (document.getElementById("moreThanHundred").checked && !tags.includes("morethanhundred"))) {
        return false;
      } else {
        return true;
      }
    }
  }

  const filter = () => {
    var filteredGames = allGames.filter(game => checkFilters(game.id));
    const search = document.getElementById("searchFilters").value;
    filteredGames = filteredGames.filter(game => (game.title.toLowerCase()).includes(search.toLowerCase()))
    setGames(filteredGames);
  }

  const resetGames = () => {
    document.getElementById("action").checked = false;
    document.getElementById("adventure").checked = false;
    document.getElementById("rpg").checked = false;
    document.getElementById("puzzle").checked = false;
    document.getElementById("singleplayer").checked = false;
    document.getElementById("coop").checked = false;
    document.getElementById("mmo").checked = false;
    document.getElementById("free").checked = false;
    document.getElementById("lessThanFive").checked = false;
    document.getElementById("lessThanTwenty").checked = false;
    document.getElementById("lessThanFifty").checked = false;
    document.getElementById("lessThanHundred").checked = false;
    document.getElementById("moreThanHundred").checked = false;
    document.getElementById("searchFilters").value = "";
    filter();
  }

  return (
    <div className="explore">
      <div className="row no-gutters">
        <div className="content exploreFiltersContent col-" style={{ padding: '10px'}}>
          <h5>{text.filtersTitle1}</h5>
          <input className="colored" type="text" id="searchFilters" onChange={filter} /> <br/>

          <h5>{text.filtersTitle2}</h5>
          <form style={{fontSize: '75%', marginLeft: '10px'}} onChange={filter}>
            <input type="radio" name="radioFilters" id="action" /> <label for="action">{text.action}</label> <br/>
            <input type="radio" name="radioFilters" id="adventure" /> <label for="adventure">{text.adventure}</label> <br/>
            <input type="radio" name="radioFilters" id="rpg" /> <label for="rpg">{text.rpg}</label> <br/>
            <input type="radio" name="radioFilters" id="puzzle" /> <label for="puzzle">{text.puzzle}</label> <br/>
            <input type="radio" name="radioFilters" id="singleplayer" /> <label for="singleplayer">{text.singleplayer}</label> <br/>
            <input type="radio" name="radioFilters" id="coop" /> <label for="coop">{text.coop}</label> <br/>
            <input type="radio" name="radioFilters" id="mmo" /> <label for="mmo">{text.mmo}</label> <br/>
          </form>

          <h5>{text.filtersTitle3}</h5>
          <form style={{fontSize: '75%', margin: '0 0 1.5vh 10px'}} onChange={filter}>
            <input type="radio" name="radioPrice" value="free" id="free" /> <label for="free">{text.filtersFree}</label> <br/>
            <input type="radio" name="radioPrice" value="lessThanFive" id="lessThanFive" /> <label for="lessThanFive">$0.01-$4.99</label> <br/>
            <input type="radio" name="radioPrice" value="lessThanTwenty" id="lessThanTwenty" /> <label for="lessThanTwenty">$5.00-$19.99</label> <br/>
            <input type="radio" name="radioPrice" value="lessThanFifty" id="lessThanFifty" /> <label for="lessThanFifty">$20.00-$49.99</label> <br/>
            <input type="radio" name="radioPrice" value="lessThanHundred" id="lessThanHundred" /> <label for="lessThanHundred">$50.00-$99.99</label> <br/>
            <input type="radio" name="radioPrice" className="colored" value="moreThanHundred" id="moreThanHundred" /> <label for="moreThanHundred">$100.00+</label> <br/>
          </form>

          <button className="filterButtons colored" id="reset" onClick={resetGames}>{text.filtersButton1}</button>
          <button className="filterButtons colored" id="FAQ">{text.filtersButton2}</button>

        </div>
        <div className="content exploreContent col-lg row justify-content-start">
          {games.map((game) =>  (
            <Card style={{ height: '30vh', width: '17.5vw', margin: '15px 15px 0px 15px' }} key={game.id} onClick={() => {cardClick(game.id)}}>
              <Card.Img variant="top" src={game.image} style={{height: '18vh', width: '17.35vw'}} />
              <Card.Body className="colored cardBody" style={{ overflow: 'auto'}}>
                <Card.Title className="cardTitle"> {game.title} </Card.Title>
              </Card.Body>
            </Card>
          ))}
        </div>
      </div>
    </div>
  );
}

export default Explore;
