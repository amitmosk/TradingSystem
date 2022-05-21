
const Header = ({ title }) => {

    return (
        <header position="center" align="center" alignSelf="center" textAlign="center" className="header">
            <h1 position="center" align="center" alignSelf="center" textAlign="center">{title}</h1>
        </header>
    )
}

Header.defaultProps = {
    title: "Market System",
}

// Header.propTypes = {
//     title: propTypes.string.isRequired,
// }

export default Header