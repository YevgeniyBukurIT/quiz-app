import React, { useState } from "react";
import { Modal, Button, Form } from "react-bootstrap";
import classes from "../../Test.module.css"
import "bootstrap/dist/css/bootstrap.min.css";
import axios from "axios";
import {useNavigate} from "react-router-dom";

interface SignUpProps {
    show: boolean;
    onClose: () => void;
}

const SignIn: React.FC<SignUpProps> = ({ show, onClose }) => {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const navigate = useNavigate();
    const [close,setClose] = useState(false);

    const handleEmailChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setEmail(event.target.value);
    };

    const handlePasswordChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setPassword(event.target.value);
    };


    const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        axios.post('http://localhost:8080/api/auth/signin',{email,password})
            .then(response => {
                sessionStorage.setItem("user",JSON.stringify(response.data))
                navigate("/choose")
            })
            .catch(error => console.log(error));
        console.log('Signing up...', email, password);

        onClose();
    };

    return (
        <div className={classes.field + " rounded-4 p-4 align-items-center flex-column"} hidden={show} onAbort={onClose}>

            <span className="font-semibold text-lg ms-auto me-auto p-4">Логін</span>
            <Form className="text-sm mt-3 ms-auto me-auto" onSubmit={handleSubmit}>
                <Form.Group className="ms-auto me-auto" controlId="formBasicEmail">
                    <input
                        type="email"
                        placeholder="Enter email"
                        value={email}
                        className="border-2  ms-auto me-auto w-100 mb-3  p-2 border-black rounded-3 bg-transparent"
                        onChange={handleEmailChange}
                        required
                    />
                </Form.Group>

                <Form.Group controlId="formBasicPassword">
                    <input
                        className="border-2 p-2 w-100 border-black rounded-3 bg-transparent"
                        type="password"
                        placeholder="Password"
                        value={password}
                        onChange={handlePasswordChange}
                        required
                    />
                </Form.Group>
                <div className="bg-slate-900 m-4 w-75 d-flex rounded-3 text-amber-300">
                <Button variant="" className="text-white ms-auto me-auto text-center" type="submit">
                    Ввійти
                </Button>
                </div>
            </Form>
        </div>
    );
};

export default SignIn;