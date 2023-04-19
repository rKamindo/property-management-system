import axios from 'axios'

const getAuthConfig = () => ({
    headers: {
        Authorization: `Bearer`
    }
})

export const registerUser = async(user) => {
    try {
        return await axios.post(
            `http://localhost:8080/api/v1/register`,
            user
        )
    } catch (e) {
        throw e;
    }
}

export const getTenants = async() => {
    try {
        return await axios.get(
            `http://localhost:8080/api/v1/tenants`
        )
    } catch (e) {
        throw(e);
    }
}

export const saveTenant = async(tenant) => {
    try {
        return await axios.post(
            `http://localhost:8080/api/v1/tenants`
        , tenant)
    } catch(e) {
        throw(e);
    }
}

export const updateTenant = async(id, update) => {
    try {
        return await axios.put(
            `http://localhost:8080/api/v1/tenants/${id}`,
            update
        )
    } catch(e) {
        throw(e);
    }
}

export const deleteTenant = async(id) => {
    try {
        return await axios.delete(
            `http://localhost:8080/api/v1/tenants/${id}`
        )
    } catch(e) {
            throw(e);
        }
}