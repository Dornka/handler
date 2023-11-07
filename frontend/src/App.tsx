import {useEffect, useState} from "react";
import {Person} from "./model/Person.tsx";
import axios from "axios";
import {Route, Routes} from "react-router-dom";
import PersonGallery from "./components/PersonGallery.tsx";

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
    function deletePerson (id: string){
        axios.delete("/api/persons/" + id)
            .then(() =>{
                setPersons(persons?.filter((persons) => persons.id !==id))
            })
    }

    return (
        <>
            <Routes>
                <Route path="/" element={<PersonGallery persons={persons} onDelete={deletePerson}/>}/>
                <Route path="/*" element={<PersonGallery persons={persons} onDelete={deletePerson}/>}/>
            </Routes>
        </>
    )
}
