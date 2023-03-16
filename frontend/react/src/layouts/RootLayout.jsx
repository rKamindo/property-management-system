import {Outlet} from "react-router-dom";
import Navbar from "../components/Navbar.jsx";
import {Center, Flex, Grid, GridItem} from "@chakra-ui/react";
import Sidebar from "../components/Sidebar.jsx";

export default function RootLayout() {
    return (
        <Flex>
            <Sidebar/>
            <Outlet/>
        </Flex>

    )
}