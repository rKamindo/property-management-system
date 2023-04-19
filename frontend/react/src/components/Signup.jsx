import {useAuth} from "../context/AuthContext.jsx"
import {useEffect} from "react";
import {useNavigate} from "react-router-dom";
import {Heading, Link, Stack} from "@chakra-ui/react";

const Signup = () => {
    const { user, setUserFromToken } = useAuth();
    const navigate = useNavigate();

    useEffect(() => {
        if (user) {
            navigate("/tenants")
        }
    })

    return (
        <Stack>
            <Flex>
                <Stack>
                    <Heading fontSize={'2xl'} mb={15}>Register for an account</Heading>
                    <CreateUserForm onSuccess={(token) => {
                        localStorage.setItem("access_token", token)
                        setUserFromToken()
                        navigate("/dashboard");
                    }} />
                    <Link color={"blue.500"} href={"/"}>
                        Have an account? Login now.
                    </Link>
                </Stack>
            </Flex>
        </Stack>
    )
}