import {
    Button,
    Drawer,
    DrawerBody,
    DrawerCloseButton,
    DrawerContent,
    DrawerFooter,
    DrawerHeader,
    DrawerOverlay,
    useDisclosure
} from "@chakra-ui/react";
import React from "react";
import CreateTenantForm from "./CreateTenantForm.jsx";
import {FiUserPlus} from "react-icons/fi";

const CreateTenantDrawer = ({fetchTenants}) => {
    const { isOpen, onOpen, onClose } = useDisclosure();
    const firstField = React.useRef()

    return (
        <>
            <Button
                leftIcon={<FiUserPlus/>}
                colorScheme={"teal"}
                onClick={onOpen}
            >
                Create Tenant
            </Button>
            <Drawer
                size={'md'}
                isOpen={isOpen}
                placement='right'
                initialFocusRef={firstField}
                onClose={onClose}
            >
                <DrawerOverlay />
                <DrawerContent>
                    <DrawerCloseButton />
                    <DrawerHeader borderBottomWidth='1px'>
                        Create a new tenant
                    </DrawerHeader>
                    <DrawerBody>
                        <CreateTenantForm
                            onSuccess={fetchTenants}
                        />
                    </DrawerBody>
                    <DrawerFooter>
                        <Button
                            colorScheme={"red"}
                            onClick={onClose}>
                            Close
                        </Button>
                    </DrawerFooter>
                </DrawerContent>
            </Drawer>
        </>

    )
}

export default CreateTenantDrawer;