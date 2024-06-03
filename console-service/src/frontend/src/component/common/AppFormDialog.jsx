import {
    Button,
    Dialog,
    DialogActions,
    DialogContent,
    DialogContentText,
    DialogTitle,
} from "@mui/material";

export default function AppFormDialog(
    {
        title = "Title",
        subTitle = "",
        actionButtonLabel = "Ok",
        open,
        setFormData,
        handleClose,
        children
    }
) {
    return (
        <Dialog
            disableEscapeKeyDown
            open={open}
            onClose={handleClose}
            PaperProps={{
                component: 'form',
                onSubmit: (event) => {
                    event.preventDefault();
                    const formData = new FormData(event.currentTarget);
                    const formJson = Object.fromEntries(formData.entries());
                    setFormData(formJson)
                    handleClose();
                },
            }}
        >
            <DialogTitle>{title}</DialogTitle>
            <DialogContent>
                <DialogContentText sx={{mb: 1}}>
                    {subTitle}
                </DialogContentText>
                {children}
            </DialogContent>
            <DialogActions>
                <Button onClick={handleClose}>Cancel</Button>
                <Button type="submit">{actionButtonLabel}</Button>
            </DialogActions>
        </Dialog>
    )
}