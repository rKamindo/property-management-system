import {Form, Formik, useField} from 'formik';
import {Alert, AlertIcon, Box, Button, DrawerFooter, FormLabel, Input, Select, Spacer, Stack} from "@chakra-ui/react";
import {saveTenant} from "../services/client.js";
import * as Yup from 'yup';
import {successNotification} from "../services/notification.js";

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

const CreateTenantForm = ({onSuccess}) => {

    return (
        <>
            <Formik
                initialValues={{
                    name: '',
                    email: '',
                    phone: '',
                    gender: ''
                }}
                validationSchema={Yup.object({
                    name: Yup.string()
                        .max(20, 'Must be 20 characters or less')
                        .required('Required'),
                    email: Yup.string()
                        .email('Must be an email')
                        .required('Required'),
                    phone: Yup.string()
                        .max(11, 'Must be 11 digits or less')
                        .required('Required'),
                    gender: Yup.string()
                        .oneOf(
                            ['MALE', 'FEMALE'],
                            'Invalid gender'
                        )
                        .required('Required'),
                })}
                onSubmit={(tenant, {setSubmitting}) => {
                    setSubmitting(true);
                    console.log(tenant);
                    saveTenant(tenant)
                        .then(res => {
                            console.log(res)
                            successNotification(
                                'Tenant added',
                                `${tenant.name} was successfully added`
                            )
                            onSuccess();
                        }).catch(err => {
                            console.log(err);
                            // TODO error notification
                    }).finally(() => {
                        setSubmitting(false);
                    })
                }}
            >
                {({isValid, isSubmitting}) => (
                <Form>
                    <Stack spacing={'24px'}>
                        <MyTextInput
                            label="Name"
                            name="name"
                            type="text"
                            placeholder="Enter name"
                        />
                        <MyTextInput
                            label="Email"
                            name="email"
                            type="email"
                            placeholder="Enter email"
                        />
                        <MyTextInput
                            label="Phone"
                            name="phone"
                            type="tel"
                            placeholder="Enter phone number"
                        />
                        <MySelect label="Gender" name="gender">
                            <option value="">Select gender</option>
                            <option value="MALE">Male</option>
                            <option value="FEMALE">Female</option>
                        </MySelect>
                        <Button
                            type="submit"
                            disabled={ !isValid || isSubmitting}
                            colorScheme={(!isValid || isSubmitting) ?
                                "gray" : "teal"
                            }>Submit</Button>
                    </Stack>
                </Form>
                )}
            </Formik>
        </>
    )
}

export default CreateTenantForm;