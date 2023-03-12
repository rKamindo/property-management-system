import {SimpleGrid} from "@chakra-ui/react";
import Tenant from "../Tenant.jsx";

export default function Dashboard() {

    return (
        <SimpleGrid p={10} columns={4} spacing={10} minChildWidth={300}>
            <Tenant />
        </SimpleGrid>
    )
}