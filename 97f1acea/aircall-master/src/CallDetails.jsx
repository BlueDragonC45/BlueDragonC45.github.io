import React from 'react';

const CallDetails = (props) => {
    const call = props.call;
    const date = new Date(call.created_at);

    return (
        <div className='border border-1 border-dark rounded-3 p-2 m-2 h-50'>
            <div className="row d-flex align-items-center w-100 mx-0">

                <div className='col-1 text-center px-2'>
                    {call.direction === "inbound" ? 
                        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" className="bi bi-telephone-inbound-fill" viewBox="0 0 16 16">
                            <path fillRule="evenodd" d="M1.885.511a1.745 1.745 0 0 1 2.61.163L6.29 2.98c.329.423.445.974.315 1.494l-.547 2.19a.68.68 0 0 0 .178.643l2.457 2.457a.68.68 0 0 0 .644.178l2.189-.547a1.75 1.75 0 0 1 1.494.315l2.306 1.794c.829.645.905 1.87.163 2.611l-1.034 1.034c-.74.74-1.846 1.065-2.877.702a18.6 18.6 0 0 1-7.01-4.42 18.6 18.6 0 0 1-4.42-7.009c-.362-1.03-.037-2.137.703-2.877zM15.854.146a.5.5 0 0 1 0 .708L11.707 5H14.5a.5.5 0 0 1 0 1h-4a.5.5 0 0 1-.5-.5v-4a.5.5 0 0 1 1 0v2.793L15.146.146a.5.5 0 0 1 .708 0"/>
                        </svg> :
                        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" className="bi bi-telephone-outbound-fill" viewBox="0 0 16 16">
                            <path fillRule="evenodd" d="M1.885.511a1.745 1.745 0 0 1 2.61.163L6.29 2.98c.329.423.445.974.315 1.494l-.547 2.19a.68.68 0 0 0 .178.643l2.457 2.457a.68.68 0 0 0 .644.178l2.189-.547a1.75 1.75 0 0 1 1.494.315l2.306 1.794c.829.645.905 1.87.163 2.611l-1.034 1.034c-.74.74-1.846 1.065-2.877.702a18.6 18.6 0 0 1-7.01-4.42 18.6 18.6 0 0 1-4.42-7.009c-.362-1.03-.037-2.137.703-2.877zM11 .5a.5.5 0 0 1 .5-.5h4a.5.5 0 0 1 .5.5v4a.5.5 0 0 1-1 0V1.707l-4.146 4.147a.5.5 0 0 1-.708-.708L14.293 1H11.5a.5.5 0 0 1-.5-.5"/>
                        </svg>
                    }
                </div>
                
                <div className='col-4 p-0'>
                    <div>To: {call.to}</div>
                    <div>Duration: {call.duration}</div>
                </div>

                <div className='col-3 p-0'>
                    <div>From: {call.from}</div>
                    <div>Via: {call.via}</div>
                </div>

                <div className='col-1 text-center px-2'>
                    {call.call_type === "answered" ? 
                        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="green" className="bi bi-check-lg" viewBox="0 0 16 16">
                            <path d="M12.736 3.97a.733.733 0 0 1 1.047 0c.286.289.29.756.01 1.05L7.88 12.01a.733.733 0 0 1-1.065.02L3.217 8.384a.757.757 0 0 1 0-1.06.733.733 0 0 1 1.047 0l3.052 3.093 5.4-6.425z"/>
                        </svg> : 
                        call.call_type === "missed" ? 
                            <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="red" className="bi bi-x" viewBox="0 0 16 16">
                                <path d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708"/>
                            </svg> : 
                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="black" className="bi bi-voicemail" viewBox="0 0 16 16">
                                <path d="M7 8.5A3.5 3.5 0 0 1 5.95 11h4.1a3.5 3.5 0 1 1 2.45 1h-9A3.5 3.5 0 1 1 7 8.5m-6 0a2.5 2.5 0 1 0 5 0 2.5 2.5 0 0 0-5 0m14 0a2.5 2.5 0 1 0-5 0 2.5 2.5 0 0 0 5 0"/>
                            </svg>
                    }
                </div>
                
                <div className='col-2 text-end px-2'>
                    <div>{date.toLocaleDateString()}</div>
                    <div>{date.toLocaleTimeString()}</div>
                </div>

                <div className='col-1 text-end px-2'>
                    <button type="button" className="btn btn-outline-dark rounded-1 p-2" onClick={() => props.archiveFunction(call.id)}>
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-archive-fill" viewBox="0 0 16 16">
                        <path d="M12.643 15C13.979 15 15 13.845 15 12.5V5H1v7.5C1 13.845 2.021 15 3.357 15zM5.5 7h5a.5.5 0 0 1 0 1h-5a.5.5 0 0 1 0-1M.8 1a.8.8 0 0 0-.8.8V3a.8.8 0 0 0 .8.8h14.4A.8.8 0 0 0 16 3V1.8a.8.8 0 0 0-.8-.8z"/>
                    </svg>
                    </button>
                </div>
            </div>
        </div>
    );
};

export default CallDetails;
