import {Outlet} from "react-router-dom";
import {Center, Flex} from "@chakra-ui/react";
import Sidebar from "../components/Sidebar.jsx";

export default function RootLayout() {
    return (
        <Flex>
            <Sidebar/>
            <Center m="auto">
                <Outlet/>
            </Center>
        </Flex>

    )
}