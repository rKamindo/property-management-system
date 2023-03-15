import {Form, Formik, useField} from 'formik';
import {Alert, AlertIcon, Box, Button, DrawerFooter, FormLabel, Input, Select, Stack} from "@chakra-ui/react";
import {saveTenant} from "../services/client.js";
import React, {useRef} from "react";

const MyTextInput = ({label, ...props}) => {
    // useField() returns [formik.getFieldProps(), formik.getFieldMeta()]
    // which we can spread on <input>. We can use field meta to show an error
    // message if the field is invalid and it has been touched (i.e. visited)

    const [field, meta] = useField(props);
    return (
        <Box>
            <FormLabel htmlFor={props.id || props.name}>{label}</FormLabel>
            <Input className="text-input" {...field} {...props} />
            {meta.touched && meta.error ? (
                <Alert className="error" status={"error"} mt={2}>
                    <AlertIcon/>
                    {meta.error}
                </Alert>
            ) : null}
        </Box>
    )
}

const MySelect = ({label, ...props}) => {
    const [field, meta] = useField(props);
    return (
        <Box>
            <FormLabel htmlFor={props.id || props.name}>{label}</FormLabel>
            <Select {...field} {...props} />
            {meta.touched && meta.error ? (
                <Alert className="error" status={"error"} mt={2}>
                    <AlertIcon/>
                    {meta.error}
                </Alert>
            ) : null}
        </Box>
    );
}

const CreateTenantForm = ({onSuccess, onSubmitting}) => {
    const formRef = useRef(null);

    return (
        <>
            <Formik
                initialValues={{
                    name: '',
                    email: '',
                    phone: '',
                    gender: ''
                }}
                onSubmit={(tenant, {setSubmitting}) => {
                    setSubmitting(true);
                    console.log(tenant);
                    saveTenant(tenant)
                        .then(res => {
                            console.log(res)
                            // TODO success notification
                            onSuccess();
                        }).catch(err => {
                            console.log(err);
                            // TODO error notification
                    }).finally(() => {
                        setSubmitting(false);
                    })
                }}
            >
                {({isSubmitting}) => (
                <Form ref={formRef}>
                    <Stack spacing={'24px'}>
                        <MyTextInput
                            label="Name"
                            name="name"
                            type="text"
                            placeholder="John"
                        />
                        <MyTextInput
                            label="Email"
                            name="email"
                            type="email"
                            placeholder="john@formik.com"
                        />
                        <MyTextInput
                            label="Phone"
                            name="phone"
                            type="tel"
                            placeholder="1234567890"
                        />
                        <MySelect label="Gender" name="gender">
                            <option value="">Select gender</option>
                            <option value="MALE">Male</option>
                            <option value="FEMALE">Female</option>
                        </MySelect>
                            <Button disabled={isSubmitting} type="submit" colorScheme='blue'>Submit</Button>
                    </Stack>
                </Form>
                )}
            </Formik>
        </>
    )
}

export default CreateTenantForm;