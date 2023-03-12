import axios from 'axios'

export const getTenants = async () => {
    try {
        return await axios.get(
            `http://localhost:8080/api/v1/tenants`
        )
    } catch (e) {
        throw(e);
    }
}