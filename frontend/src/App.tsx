import {useEffect, useState} from "react";
import {Person} from "./model/Person.tsx";
import axios from "axios";

export default function App(){

    const  [persons, setPersons] = useState<Person[]>()
    const uri: string = "/api/persons"
    useEffect(() => {
        getAll()
    }, []);

    function getAll() {
        axios.get(uri)
            .then(response => {setPersons(response.data)
            })
            .catch(() => {
                alert('Fehler: Gib alle erforderlichen Daten ein.')
            })
    }

    return (
        <>
            {persons && persons.map((person) => (
                <div key={person.id}>
                    <p>Name: {person.firstName}</p>
                    <p>Age: {person.lastName}</p>
                    <p>Age: {person.address.addressCity}</p>
                    <p>Age: {person.address.addressPLZ}</p>
                    <p>Age: {person.address.addressStreet}</p>
                    <p>Age: {person.address.addressHouseNumber}</p>
                    {/* Add more properties as needed */}
                </div>
            ))}
        </>
    );
}
