import {Person} from "../model/Person.tsx";

type PersonCardProps = {

    person: Person
    onDelete: (id: string) => void
}
export default function PersonCard(props: PersonCardProps){

    const onHandleDelete = (id: string) => {
        props.onDelete(id);
    };

    return (

        <article>
            <h4>{props.person.firstName} {props.person.lastName}</h4>
            <p>{props.person.address.addressCity} {props.person.address.addressPLZ}</p>
            <p>{props.person.address.addressStreet} {props.person.address.addressHouseNumber}</p>
            <button onClick={() => props.person.id && onHandleDelete(props.person.id)}>Delete</button>
        </article>
    )
}