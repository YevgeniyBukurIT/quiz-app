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

const SignUp: React.FC<SignUpProps> = ({ show, onClose }) => {
    const [name, setName] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");
    const navigate = useNavigate();
    const handleNameChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setName(event.target.value);
    };

    const handleEmailChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setEmail(event.target.value);
    };

    const handlePasswordChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setPassword(event.target.value);
    };

    const handleConfirmPasswordChange = (
        event: React.ChangeEvent<HTMLInputElement>
    ) => {
        setConfirmPassword(event.target.value);
    };

    const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        axios.post('http://localhost:8080/api/auth/signup',{
            firstname: name,lastname: name,email,password})
            .then(response => {
                sessionStorage.setItem("user",JSON.stringify(response.data))
                navigate("/choose")
            })
            .catch(error => console.log(error));
        console.log('Signing up...', email, password,name,name);

        onClose();
    };

    return (
        <div className={classes.field + " rounded-4 p-4 align-items-center flex-column"} hidden={show} onAbort={onClose}>
            <span className="font-semibold text-lg ms-auto me-auto p-4">Реєстрація</span>
            <Form className="text-sm mt-3 ms-auto me-auto" onSubmit={handleSubmit}>
                <Form.Group controlId="formBasicName">
                    <input
                        type="text"
                        className="border-2  ms-auto me-auto w-100 mb-3  p-2 border-black rounded-3 bg-transparent"
                        placeholder="Enter your name"
                        value={name}
                        onChange={handleNameChange}
                        required
                    />
                </Form.Group>

                <Form.Group controlId="formBasicEmail">
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
                        type="password"
                        placeholder="Password"
                        value={password}
                        className="border-2  ms-auto me-auto w-100 mb-3  p-2 border-black rounded-3 bg-transparent"
                        onChange={handlePasswordChange}
                        required
                    />
                </Form.Group>

                <Form.Group controlId="formBasicConfirmPassword">
                    <input
                        className="border-2  ms-auto me-auto w-100 mb-3  p-2 border-black rounded-3 bg-transparent"
                        type="password"
                        placeholder="Confirm Password"
                        value={confirmPassword}
                        onChange={handleConfirmPasswordChange}
                        required
                    />
                </Form.Group>

                <div className="bg-slate-900 m-4 mb-1 w-75 d-flex rounded-3 text-amber-300">
                    <Button variant="" className="text-white ms-auto me-auto text-center" type="submit">
                        Зареєструватися
                    </Button>
                </div>
            </Form>
        </div>
    );
};

export default SignUp;