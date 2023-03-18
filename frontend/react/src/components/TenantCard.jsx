import {
    Text,
    Box,
    Center,
    Heading,
    Image,
    Stack,
    Tag,
    useDisclosure,
    useColorModeValue,
    Button,
    AlertDialogOverlay,
    AlertDialogContent,
    AlertDialogHeader,
    AlertDialogBody, AlertDialogFooter, AlertDialog, Badge, HStack
} from "@chakra-ui/react";
import React, {useRef} from "react";
import {deleteTenant} from "../services/client.js";
import {errorNotification, successNotification} from "../services/notification.js";
import * as PropTypes from "prop-types";
import UpdateTenantDrawer from "./UpdateTenantDrawer.jsx";

export default function CardWithImage({id, name, email, phone, gender, apartmentNumber, fetchTenants}) {
    const {isOpen, onOpen, onClose} = useDisclosure();
    const cancelRef = useRef();
    const isResident = Boolean(apartmentNumber);

    return (
        <Center py={6}>
            <Box
                maxW={'250px'}
                minW={'250px'}
                w={'full'}
                m={'2px'}
                rounded={'md'}
                bg={useColorModeValue('white', 'gray.800')}
                boxShadow={'lg'}
                overflow={'hidden'}
            >
                <Image
                    h={'120px'}
                    w={'full'}
                    src={
                    'https://images.unsplash.com/photo-1612865547334-09cb8cb455da?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=634&q=80'
                    }
                    objectFit={'cover'}
                />
                <Box p={6}>
                    <Stack spacing={2} align={'center'} mb={5}>
                       <Tag borderRadius={'full'}>{id}</Tag>
                       <Heading fontSize={'2xl'} fontWeight={500} fontFamily={'body'}>
                           {name}
                       </Heading>
                        <Text color={'gray.500'}>{email}</Text>
                        <Text color={'gray.500'}>{phone} | {gender}</Text>
                        <HStack>
                            <Badge colorScheme={isResident ? "green" : "red"}>{isResident ? "Resident" : "Non-Resident"}    </Badge>
                            {isResident && <Text color={'gray.500'}>Unit {apartmentNumber}</Text>}
                        </HStack>}
                    </Stack>
                    <Stack direction={'row'} justify={'center'}>
                        <Stack>
                            <UpdateTenantDrawer
                                initialValues={{name, email, phone}}
                                tenantId={id}
                                fetchTenants={fetchTenants}
                            />
                        </Stack>
                        <Stack>
                            <Button
                                colorScheme={'red'}
                                rounded={'full'}
                                _hover={{
                                    transform: 'translateY(-2px)',
                                    boxShadow:'lg'
                                }}
                                _active={{
                                    bg: 'green.500'
                                }}
                                onClick={onOpen}
                            >
                                Delete
                            </Button>
                            <AlertDialog leastDestructiveRef={cancelRef} isOpen={isOpen} onClose={onClose}>
                                <AlertDialogOverlay>
                                    <AlertDialogContent>
                                        <AlertDialogHeader>
                                            Delete Tenant
                                        </AlertDialogHeader>
                                        <AlertDialogBody>
                                            Are you sure you want to delete this tenant?
                                        </AlertDialogBody>
                                        <AlertDialogFooter>
                                            <Button ref={cancelRef} onClick={onClose}>
                                                Cancel
                                            </Button>
                                            <Button colorScheme='red' onClick={() => {
                                                deleteTenant(id).then(res => {
                                                    console.log(res);
                                                    successNotification(
                                                        'Tenant deleted',
                                                        `${name} was successfully deleted`
                                                    )
                                                    fetchTenants();

                                                }).catch(err => {
                                                    console.log(err);
                                                    errorNotification(
                                                        err.code,
                                                        err.response.data.message
                                                    )
                                                }).finally(() => {
                                                    onClose();
                                                })
                                            }} ml={3}
                                            >
                                                Delete
                                            </Button>
                                        </AlertDialogFooter>
                                    </AlertDialogContent>
                                </AlertDialogOverlay>
                            </AlertDialog>
                        </Stack>
                    </Stack>
                </Box>
            </Box>
        </Center>
    )
}