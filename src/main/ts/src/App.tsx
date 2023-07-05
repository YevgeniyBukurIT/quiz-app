import React from 'react';
import './App.css';
import {BrowserRouter as Router, Route, Routes} from "react-router-dom";
import {WelcomePage} from "./page/HelloPage";
import MainPage from "./page/MainPage";
import HomePage from "./page/MainPage";
import TestTaking from "./page/TestTaking";
import TestCreationPage from "./page/TestCreationPage";
import ResultPage from "./page/ResultPage";
import UserResult from "./page/UserResultPage";
import {UsersResultList} from "./page/UsersResultList";
import {RoleChooser} from "./page/RoleChooser";
import TestInfoPage from "./page/TestInfoPage";
import {TestList} from "./page/TestList";

function App() {
    return (
        <div className="App h-100 min-h-screen ">
            <div className="container h-100 d-flex justify-content-center">
                <div className="w-75 row h-100 d-flex">
                    <Routes>
                        <Route path="/" element={<WelcomePage/>}/>
                        <Route path="/choose" element={<RoleChooser/>}/>
                        <Route path="/main" element={<HomePage/>}/>
                        <Route path="/user/list" element={<TestList isCreator={false}/>}/>
                        <Route path="/creator/list" element={<TestList isCreator={true}/>}/>
                        <Route path="/test/session/" element={<TestTaking/>}/>
                        <Route path="/test/result/:testId/:userId" element={<UserResult/>}/>
                        <Route path="/test/creator/result/:testId/" element={<ResultPage/>}/>
                        <Route path="/test/create/" element={<TestCreationPage/>}></Route>
                        <Route path="/test/creator/result/:testId/list" element={<UsersResultList/>}></Route>
                        <Route path="/test/creator/overview/:testId" element={<TestInfoPage/>}></Route>
                    </Routes>
                </div>
            </div>
        </div>
    );
}

export default App;
