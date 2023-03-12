import { useEffect, useState } from 'react'
import { getTenants } from './services/client.js'
import {Card, CardBody, CardFooter, CardHeader} from "@chakra-ui/react";

const Tenant = () => {
    const [tenants, setTenants] = useState([]);

    const fetchTenants = () => {
        getTenants().then(res => {
            setTenants(res.data);
        })
    }

    useEffect(() => {
        fetchTenants();
    }, [])

    if(tenants.length <= 0) {
        return (
            <h1>No tenants available</h1>
        )
    }

    return (
        tenants && tenants.map(tenant => (
            <Card key={tenant.id} borderTop="8px" borderColor="purple.400" bg="white">
                <CardHeader>
                    Card Header
                </CardHeader>
                <CardBody>
                    Card Body
                </CardBody>
                <CardFooter>
                    Card Footer
                </CardFooter>
            </Card>
        ))
    );
}

export default Tenant;