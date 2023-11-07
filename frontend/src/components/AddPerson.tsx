import {Person} from "../model/Person.tsx";
import axios from "axios";
import {Link, useNavigate} from "react-router-dom";
import Header from "./Header.tsx";
import {ChangeEvent, FormEvent, useState} from "react";

type AddPersonProps = {
    uri: string,
    getAll: () => void
}

export default function AddPerson(props: AddPersonProps){

    const navigate = useNavigate()

    const [firstName, setFirstName] = useState("")
    const [lastName, setLastName] = useState("")
    const [addressCity, setAddressCity] = useState("")
    const [addressPLZ, setAddressPLZ] = useState("")
    const [addressStreet, setAddressStreet] = useState("")
    const [addressHouseNumber, setAddressHouseNumber] = useState("")
    function savePerson(newPerson: Person){

        axios.post(props.uri, newPerson)
            .then(props.getAll)
            .then(() => navigate("/"))
            .catch((error) => {
                alert("Error: " + error.response.data)
                navigate("/persons/add")
            })
    }

    function onFormSubmit (event: FormEvent<HTMLFormElement>) {
        event.preventDefault()
        const person: Person = {
            firstName: firstName,
            lastName: lastName,
            address: {
            addressPLZ: addressPLZ,
            addressCity: addressCity,
            addressStreet: addressStreet,
            addressHouseNumber: addressHouseNumber
        }
        }

        savePerson(person)
    }



    function onFirstNameChange(event: ChangeEvent<HTMLInputElement>){
    setFirstName(event.target.value)}

    function onLastNameChange(event: ChangeEvent<HTMLInputElement>){
        setLastName(event.target.value)}

    function onAddressCityChange(event: ChangeEvent<HTMLInputElement>){
        setAddressCity(event.target.value)}
    function onAddressPLZChange(event: ChangeEvent<HTMLInputElement>){
        setAddressPLZ(event.target.value)}
    function onAddressStreetChange(event: ChangeEvent<HTMLInputElement>){
        setAddressStreet(event.target.value)}
    function onAddressHouseNumberChange(event: ChangeEvent<HTMLInputElement>){
        setAddressHouseNumber(event.target.value)}

    return (

        <>
        <Header/>

            <form onSubmit={onFormSubmit}>
            <label>
                FirstName
            </label>
            <input name="FirstName" value={firstName} onChange={onFirstNameChange}/>
                <label>
                LastName
            </label>
                <input name="LastName" value={lastName} onChange={onLastNameChange}/>
                <label>
                    City
                </label>
                <input name="addressCity" value={addressCity} onChange={onAddressCityChange}/>
                <label>
                    PLZ
                </label>
                <input name="addressPLZ" value={addressPLZ} onChange={onAddressPLZChange}/>
                <label>
                    Street
                </label>
                <input name="addressStreet" value={addressStreet} onChange={onAddressStreetChange}/>
                <label>
                    Housenumber
                </label>
                <input name="addressHousenumber" value={addressHouseNumber} onChange={onAddressHouseNumberChange}/>


                <button>
                    Save
                </button>
                <Link to={"/"}>
                    Back
                </Link>
            </form>
        </>
    )
}