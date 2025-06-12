const roleCheck = (requireRole) => {

    return (req, res, next) => {
        const userRole = req.user.role;

        if(userRole ===  requireRole) {
            next();
        }else {
            res.status(403).json({message: "Forbidden"})
        }
    }



}

module.exports  = roleCheck