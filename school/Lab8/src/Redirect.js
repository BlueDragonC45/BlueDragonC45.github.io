import { useHistory } from 'react-router-dom';

function Redirect() {
  const history = useHistory();
  history.push("/english/");


  return (
    <div className="redirect">
    </div>
  );
}

export default Redirect;
