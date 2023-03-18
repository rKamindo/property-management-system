import {Text, Menu, MenuButton, Flex, MenuList} from "@chakra-ui/react";
import {Icon} from "@chakra-ui/icons";
import {Link as RouterLink} from "react-router-dom"
import NavHoverBox from "./NavHoverBox.jsx";

export default function NavItem({linkTo, navSize, title, icon, active, description}) {
    return (
        <Flex
            mt={30}
            flexDir="column"
            w="100%"
            alignItems={navSize === "small" ? "center" : "flex-start"}
        >
            <Menu placement="right">
                <RouterLink
                    to={linkTo}
                >
                    <MenuButton
                        w="100%"
                        bgColor={active && "#AEC8CA"}
                        p={3}
                        borderRadius={8}
                        _hover={{
                            textDecor: 'none',
                            bgColor: "#AEC8CA"
                        }}
                        w={navSize === "large" && "100%"}
                    >
                        <Flex
                            align="center"
                        >
                            <Icon as={icon} fontSize="xl" color={active ? "#82AAAD" : "blackAlpha.700"}/>
                            <Text ml={5} display={navSize === "small" ? "none" : "flex"}>{title}</Text>
                        </Flex>
                    </MenuButton>
                </RouterLink>
                <MenuList borderRadius="10px" p={0}>
                    <NavHoverBox title={title} icon={icon} description={description} p={4}/>
                </MenuList>
            </Menu>
        </Flex>
    )

}