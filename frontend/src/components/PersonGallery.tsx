import PersonCard from "./PersonCard.tsx";
import {Person} from "../model/Person.tsx";
import Header from "./Header.tsx";
import {Link} from "react-router-dom";

type PersonGalleryProps = {
    persons?: Person[]
    onDelete: (id: string) => void;
}
export default function PersonGallery(props: PersonGalleryProps)       {

    return (

        <div className="div_personGallery">
            <Header/>
            <Link to={"persons/add"} className="add-button">
                Add New Person
            </Link>
            {props.persons?.map((person) => (
                <div key={person.id}>
                    <PersonCard onDelete={props.onDelete} person={person}/>
                </div>
            ))}
        </div>
    )
}