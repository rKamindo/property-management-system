import {Text, Avatar, Divider, Flex, Heading, IconButton, Spacer} from "@chakra-ui/react";
import {CalendarIcon, HamburgerIcon} from "@chakra-ui/icons";
import {useState} from "react";
import NavItem from "./NavItem.jsx";
import {FiHome,FiSettings, FiUsers} from "react-icons/fi";
import {GoDashboard} from "react-icons/go"
import {BiBuilding} from "react-icons/bi";

export default function Sidebar() {
    const [navSize, setNavSize] = useState("large");
    return (
        <Flex
            pos="sticky"
            left="5px"
            h="95vh"
            mt="2.5vh"
            boxShadow="0 4px 12px 0 rgba(0, 0, 0, 0.05)"
            borderRadius={navSize === "small" ? "15px" : "30px"}
            w={navSize === "small" ? "75px" : "200px"}
            flexDir="column"
            justify="space-between"
        >
            <Flex
                p="5%"
                flexDir="column"
                align={navSize === "small" ? "center" : "flex-start"}
                as="nav"
            >
                <IconButton
                    background="none"
                    mt={5}
                    _hover={{ background: 'none'}}
                    icon={<HamburgerIcon/>}
                    onClick={() => {
                        navSize === "small" ?
                            setNavSize("large") : setNavSize("small")
                    }}

                />
                <NavItem
                    linkTo="/"
                    navSize={navSize}
                    icon={GoDashboard}
                    title="Dashboard"
                    description="An overview of everything"
                    href={"/"}
                />
                <NavItem
                    linkTo="/tenants"
                    navSize={navSize}
                    icon={FiUsers}
                    title="Tenants"
                    description="View, add, edit, and remove tenants"
                />
                <NavItem
                    linkTo="/apartments"
                    navSize={navSize}
                    icon={FiHome}
                    title="Apartments"
                    description="View, add, edit, and remove apartments"
                />
                <NavItem
                    linkTo="/properties"
                    navSize={navSize}
                    icon={BiBuilding}
                    title="Properties"
                    description="View, add, edit, and remove properties"
                />
                <NavItem
                    navSize={navSize}
                    icon={FiSettings}
                    title="Settings"
                    description={"Edit settings"}
                />
            </Flex>
            <Flex
                p="5%"
                flexDir="column"
                justify="center"
                align={navSize === "small" ? "center" : "flex-start"}
                mb={4}
            >
                <Divider display={navSize === "small" ? "none" : "flex"}/>
                <Flex mt={4} justify="flex-end" align="center">
                    <Avatar size="sm"/>
                    <Spacer/>
                    <Flex ml={4}  flexDir="column" display={navSize === "small" ? "none" : "flex"}>
                        <Heading as="h3" size="sm" >John Doe</Heading>
                        <Text color="gray">Admin</Text>
                    </Flex>
                </Flex>
            </Flex>
        </Flex>
    )
}