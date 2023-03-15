import {Box, Button, Flex, Heading, HStack, Spacer, Text} from "@chakra-ui/react";

export default function Navbar() {
    return (
        <Flex as="nav" p="4px" alignItems="center">
            <Heading as="h1">Tenance</Heading>
            <Spacer />

            <HStack spacing="10px">
                <Box bg="gray.200" p="10px">M</Box>
                <Text>userthatsloggedin@email.com</Text>
                <Button colorScheme='teal'>Logout</Button>
            </HStack>
        </Flex>
    )
}