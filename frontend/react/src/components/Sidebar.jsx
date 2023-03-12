import {List, ListIcon, ListItem} from "@chakra-ui/react";
import {NavLink} from "react-router-dom";
import {AddIcon, AtSignIcon, CalendarIcon, EditIcon, PlusSquareIcon} from "@chakra-ui/icons";

export default function Sidebar() {
    return (
        <List color="white" fontSize="1.2em" spacing={4}>
            <ListItem>
                <NavLink to="/">
                    <ListIcon as={CalendarIcon} />
                    Dashboard
                </NavLink>
            </ListItem>
            <ListItem>
                <NavLink to="/create">
                    <ListIcon as={EditIcon} />
                    Create
                </NavLink>
            </ListItem>
            <ListItem>
                <NavLink to="/profile">
                    <ListIcon as={AtSignIcon} />
                    Profile
                </NavLink>
            </ListItem>
        </List>
    )
}