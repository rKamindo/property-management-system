import {Text, Avatar, Divider, Flex, Heading, List, ListIcon, ListItem} from "@chakra-ui/react";
import {NavLink} from "react-router-dom";
import {AddIcon, AtSignIcon, CalendarIcon, EditIcon, PlusSquareIcon} from "@chakra-ui/icons";

export default function Sidebar() {
    return (
        <Flex
            pos="sticky"
            left="5px"
            h="95vh"
            mt="2.5vh"
            boxShadow="0 4px 12px 0 rgba(0, 0, 0, 0.05)"
            flexDir="column"
            justify="space-between"
        >
            <Flex
                p="5%"
                flexDir="column"
                alignItems="flex-end"
                mb={4}
            >
                <Divider/>
                <Flex>
                    <Avatar />
                    <Flex flexDir="column" ml={4}>
                        <Heading as="h3" size="sm">Randy Kamindo</Heading>
                        <Text color="gray">Admin</Text>
                    </Flex>
                </Flex>
            </Flex>
        </Flex>
    )
}