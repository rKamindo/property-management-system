import {Flex} from "@chakra-ui/react";

export default function Navbar() {
    return (
        <Flex bg="gray.200">
            <Box w="150px" h="50px" bg="red">1</Box>
            <Box w="150px" h="50px" bg="blue">2</Box>
            <Box w="150px" h="50px" bg="green">3</Box>
            <Box w="150px" h="50px" bg="yellow">4</Box>
        </Flex>
    )
}