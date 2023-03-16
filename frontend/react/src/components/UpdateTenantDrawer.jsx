

import {
    Button,
    Drawer,
    DrawerBody, DrawerCloseButton,
    DrawerContent,
    DrawerFooter,
    DrawerHeader,
    DrawerOverlay,
    useDisclosure
} from "@chakra-ui/react";
import {CloseIcon} from "@chakra-ui/icons";
import UpdateTenantForm from "./UpdateTenantForm.jsx";

const UpdateTenantDrawer = ({fetchTenants, initialValues, tenantId}) => {
    const { isOpen, onOpen, onClose } = useDisclosure();
    return <>
        <Button
            bg={'gray.200'}
            color={'black'}
            rounded={'full'}
            _hover={{
                transform: 'translateY(-2px)',
                boxShadow: 'lg'
            }}
            onClick={onOpen}
        >
            Update
        </Button>
        <Drawer isOpen={isOpen} onClose={onClose} size={'md'}>
            <DrawerOverlay />
            <DrawerContent>
                <DrawerCloseButton/>
                <DrawerHeader>Update tenant</DrawerHeader>

                <DrawerBody>
                    <UpdateTenantForm
                        fetchTenants={fetchTenants}
                        initialValues={initialValues}
                        tenantId={tenantId}
                    />
                </DrawerBody>

                <DrawerFooter>
                    <Button
                        leftIcon={<CloseIcon/>}
                        colorScheme={'teal'}
                        onClick={onClose}
                    >
                        Close
                    </Button>
                </DrawerFooter>
            </DrawerContent>
        </Drawer>
    </>
}

export default UpdateTenantDrawer;