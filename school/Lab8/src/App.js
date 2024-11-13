import Navbar from './Navbar';
import Redirect from './Redirect';
import Breadcrumbs from './Breadcrumbs';
import Home from './Home';
import Explore from './Explore';
import Contact from './Contact';
import GameViewer from './GameViewer';
import Download from './Download';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom'

function App() {

  return (
    <Router>
      <div className="App">
        <Switch>
          <Route exact path="/">
            <Redirect />
          </Route>
          <Route path="/english/">
            <Navbar language="english" />
          </Route>
          <Route path="/francais/">
            <Navbar language="francais" />
          </Route>
        </Switch>
        <div className="Breadcrumbs">
          <Switch>
            <Route exact path="/english/">
              <Breadcrumbs path="Home" language="english" />
            </Route>
            <Route exact path="/francais/">
              <Breadcrumbs path="Home" language="francais" />
            </Route>
            <Route path="/english/explore">
              <Breadcrumbs path="Explore" language="english" />
            </Route>
            <Route path="/francais/explore">
              <Breadcrumbs path="Explore" language="francais" />
            </Route>
            <Route path="/english/contact">
              <Breadcrumbs path="Contact" language="english" />
            </Route>
            <Route path="/francais/contact">
              <Breadcrumbs path="Contact" language="francais" />
            </Route>
            <Route path="/english/gameviewer">
              <Breadcrumbs path="Gameviewer" language="english" />
            </Route>
            <Route path="/francais/gameviewer">
              <Breadcrumbs path="Gameviewer" language="francais" />
            </Route>
            <Route path="/english/download">
              <Breadcrumbs path="Download" language="english"/>
            </Route>
            <Route path="/francais/download">
              <Breadcrumbs path="Download" language="francais"/>
            </Route>
          </Switch>
        </div>
        <div className="Body">
          <Switch>
            <Route exact path="/english/">
              <Home language="english"/>
            </Route>
            <Route exact path="/francais/">
              <Home language="francais"/>
            </Route>
            <Route path="/english/explore">
              <Explore language="english"/>
            </Route>
            <Route path="/francais/explore">
              <Explore language="francais"/>
            </Route>
            <Route path="/english/contact">
              <Contact language="english"/>
            </Route>
            <Route path="/francais/contact">
              <Contact language="francais"/>
            </Route>
            <Route path="/english/gameviewer">
              <GameViewer language="english"/>
            </Route>
            <Route path="/francais/gameviewer">
              <GameViewer language="francais"/>
            </Route>
            <Route path="/english/download">
              <Download language="english"/>
            </Route>
            <Route path="/francais/download">
              <Download language="francais"/>
            </Route>
          </Switch>
        </div>
      </div>
    </Router>
  );
}

export default App;
