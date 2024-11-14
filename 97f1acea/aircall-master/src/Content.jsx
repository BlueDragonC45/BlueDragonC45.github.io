import React from 'react';
import { useState, useEffect } from 'react';

import CallDetails from './CallDetails.jsx';

const Content = () => {

    const [callList, setCallList] = useState([]);
    const [inbox, setInbox] = useState(true);

    const fetchCalls = async () => {
        await fetch('https://aircall-api.onrender.com/activities')
        .then(response => response.json())
        .then(response => setCallList(response))
        .catch(err => console.error(err));
    };

    const archiveCall = async (id) => {
        await fetch(`https://aircall-api.onrender.com/activities/${id}`, {
            method: "PATCH",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({is_archived: true})
        })
        .catch(err => console.error(err));
        fetchCalls();
    };

    const unarchiveCall = async (id) => {
        await fetch(`https://aircall-api.onrender.com/activities/${id}`, {
            method: "PATCH",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({is_archived: false})
        })
        .catch(err => console.error(err));
        fetchCalls();
    };

    const archiveAll = async () => {
        if (inbox) {
            callList.map((call) => {
                if (call.is_archived === false) {
                    archiveCall(call.id);
                }
            })
        } else {
            await fetch(`https://aircall-api.onrender.com/reset`, {
                method: "PATCH",
                headers: {
                    "Content-Type": "application/json",
                }
            })
            .catch(err => console.error(err)); 
        }
        fetchCalls();
    };

    useEffect(() => {
        fetchCalls();
    }, []);

    return (
        <div className='container-sm w-100 pb-5'>
            <div className="row justify-content-center w-100 my-3">
                <div className="col-3">{/*Spacer*/}</div>
                <button type="button" className="col-2 btn btn-success rounded-1 p-2 mx-2" onClick={() => setInbox(true)}>Inbox</button>
                <button type="button" className="col-2 btn btn-success rounded-1 p-2 mx-2" onClick={() => setInbox(false)}>Archive</button>
                <div className="col-2">{/*Spacer*/}</div>
                <button type="button" className="col-2 btn btn-success rounded-1 p-2" onClick={() => archiveAll()}>{inbox ? 'Archive All' : 'Unarchive All'}</button>
            </div>
        
            <div>
                {inbox ? (
                    <div>
                        {callList.map((call) => (
                            !call.is_archived ? <CallDetails key={call.id} call={call} archiveFunction={(id) => archiveCall(id)}/> : null
                        ))}
                    </div>
                ) : (                        
                    <div>
                        {callList.map((call) => (
                            call.is_archived ? <CallDetails key={call.id} call={call} archiveFunction={(id) => unarchiveCall(id)}/> : null
                        ))}
                    </div>
                )}
            </div>
        </div>
    );
};

export default Content;
