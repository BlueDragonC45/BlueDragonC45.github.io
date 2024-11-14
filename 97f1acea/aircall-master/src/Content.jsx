import React from 'react';
import { useState, useEffect } from 'react';

import CallDetails from './CallDetails.jsx';

const Content = () => {

    const [callList, setCallList] = useState([]);
    const [inbox, setInbox] = useState(true);

    const fetchCalls = async () => {
        await fetch('https://aircall-api.onrender.com/activities')
        .then(response => response.json())
        .then(response => {
            setCallList(response);
            console.log(response);
        })
        .catch(err => console.error(err));
    };

    useEffect(() => {
        fetchCalls();
    }, []);

    return (
        <div>
            <div className='container-sm w-100 mb-5'>
                <div className="btn-group w-25 mx-auto my-3">
                    <button type="button" className="btn btn-outline-primary p-2" onClick={() => setInbox(true)}>Inbox</button>
                    <button type="button" className="btn btn-outline-primary p-2" onClick={() => setInbox(false)}>Archive</button>
                </div>
            
                <div>
                    {inbox ? (
                        <div>
                            {callList.map((call) => (
                                !call.isArchived ? <CallDetails key={call.id} props={call}/> : null
                            ))}
                        </div>
                    ) : ("Archive")}
                </div>
            </div>
        </div>
    );
};

export default Content;
