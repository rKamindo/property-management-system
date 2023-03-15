import { useEffect, useState } from 'react'
import { getTenants } from './services/client.js'
import {Text, Spinner, Wrap, WrapItem} from "@chakra-ui/react";
import CardWithImage from "./components/TenantCard.jsx";
import CreateTenantDrawer from "./components/CreateTenantDrawer.jsx";

const Tenant = () => {
    const [tenants, setTenants] = useState([]);
    const [loading, setLoading] = useState(false);

    const fetchTenants = () => {
        setLoading(true);
        getTenants().then(res => {
            setTenants(res.data);
        }).catch(err => {
            // TODO error handling
        }).finally(() => {
            setLoading(false);
        })
    }

    useEffect(() => {
        fetchTenants();
    }, [])

    if (loading) {
        return (
            <Spinner
                thickness='4px'
                speed='0.65s'
                emptyColor='gray.200'
                color='blue.500'
                size='xl'
            />
        )
    }

    // TODO return error screen

    if (tenants.length <= 0) {
        return (
            <>
                <CreateTenantDrawer
                    fetchTenants={fetchTenants}
                />
                <Text mt={5}>No tenants available</Text>
            </>
        )
    }

    return (
        <>
            <CreateTenantDrawer
                fetchTenants={fetchTenants}
            />
            <Wrap justify={"center"} spacing={"30px"}>
                {tenants.map(tenant => (
                    <WrapItem key={tenant.id}>
                        <CardWithImage
                            {...tenant}
                            fetchTenants={fetchTenants}
                        />
                    </WrapItem>
                ))}
            </Wrap>
        </>
    )
}


export default Tenant;