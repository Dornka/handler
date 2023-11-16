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

        <table>
            <thead>
            <tr>
                <th>Name</th>
                <th>Stadt</th>
                <th>PLZ</th>
                <th>Straße</th>
                <th>Hausnummer</th>
                <th>Aktion</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>{props.person.firstName}, {props.person.lastName}</td>
                <td>{props.person.address.addressCity}</td>
                <td>{props.person.address.addressPLZ}</td>
                <td>{props.person.address.addressStreet}</td>
                <td>{props.person.address.addressHouseNumber}</td>
                <td>
                    <button className="delete-button" onClick={() => props.person.id && onHandleDelete(props.person.id)}>Delete</button>
                </td>
            </tr>
            </tbody>
        </table>
    )
}