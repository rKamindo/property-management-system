import {
    Text,
    Avatar,
    Box,
    Center,
    Flex,
    Heading,
    Image,
    Stack,
    Tag,
    useDisclosure,
    useColorModeValue
} from "@chakra-ui/react";
import React, {useRef} from "react";

export default function CardWithImage({id, name, email, phone, gender, apartmentNumber, fetchTenants}) {
    const randomUserGender = gender === "MALE" ? "men" : "women";
    const {isOpen, onOpen, onClose} = useDisclosure();
    const cancelRef = useRef();

    return (
        <Center py={6}>
            <Box
                maxW={'300px'}
                minW={'300px'}
                w={'full'}
                m={'2px'}
                bg={useColorModeValue('white', 'gray.800')}
            >
                <Image
                    h={'120px'}
                    w={'full'}
                    src={
                    'https://images.unsplash.com/photo-1612865547334-09cb8cb455da?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=634&q=80'
                    }
                    objectFit={'cover'}
                />
                <Box p={6}
                >
                    <Stack spacing={2} align={'center'} mb={5}>
                       <Tag borderRadius={'full'}>{id}</Tag>
                       <Heading fontSize={'2xl'} fontWeight={500} fontFamily={'body'}>
                           {name}
                       </Heading>
                        <Text color={'gray.500'}>{email}</Text>
                        <Text color={'gray.500'}>Phone {phone} | {gender}</Text>
                        <Text color={'gray.500'}>Apartment Number {apartmentNumber}</Text>
                    </Stack>
                </Box>
            </Box>
        </Center>
    )
}